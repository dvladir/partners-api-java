package com.dvladir.partners.data;

import com.dvladir.common.domain.PageData;
import com.dvladir.common.domain.SortField;
import com.dvladir.common.exception.ErrorCode;
import com.dvladir.partners.api.dto.PartnerSortableFields;
import com.dvladir.common.exception.PersistenceException;
import com.dvladir.partners.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.UUID;

@Repository
public class PartnerDaoImpl implements PartnerDao {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  public PartnerDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public UUID add(PartnerInfo value) throws PersistenceException {
    UUID id = null;

    try {

      // addPartnerType
      SqlParameterSource params = new MapSqlParameterSource()
          .addValue("partner_type", value.getPartnerType(), Types.OTHER);

      id = namedParameterJdbcTemplate.queryForObject(
          "insert into partner_info (partner_type) " +
              "values (:partner_type) returning id",
          params,
          UUID.class
      );

      // addAddress
      params = new MapSqlParameterSource()
          .addValue("id", id)
          .addValue("city", value.getAddressInfo().getCity())
          .addValue("street", value.getAddressInfo().getStreet())
          .addValue("house_number", value.getAddressInfo().getHouseNumber())
          .addValue("inx", value.getAddressInfo().getIdx());
      namedParameterJdbcTemplate.update(
          "insert into address (partner_id, city, street, house_number, inx) " +
              "values (:id, :city, :street, :house_number, :inx)",
          params
      );

      // add contact
      params = new MapSqlParameterSource()
          .addValue("id", id)
          .addValue("phone", value.getContactInfo().getPhone())
          .addValue("email", value.getContactInfo().getEmail());
      namedParameterJdbcTemplate.update(
          "insert into contact(partner_id, phone, email) " +
              "values (:id, :phone, :email)",
          params
      );

      if (value.getPartnerType() == PartnerType.naturalPerson) {
        // add natural person
        final PersonalInfo personalInfo = value.getPersonalInfo();

        params = new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("first_name", personalInfo.getFirstName())
            .addValue("last_name", personalInfo.getLastName())
            .addValue("middle_name", personalInfo.getMiddleName())
            .addValue("birth_date", personalInfo.getBirthDate())
            .addValue("gender", personalInfo.getGender(), Types.OTHER);

        namedParameterJdbcTemplate.update(
            "insert into personal_info(partner_id, first_name, last_name, middle_name, birth_date, gender) " +
                "values (:id, :first_name, :last_name, :middle_name, :birth_date, :gender)",
            params
        );
      }

      if (value.getPartnerType() == PartnerType.legalEntity) {
        // add legal entity
        final CompanyInfo companyInfo = value.getCompanyInfo();

        params = new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("name", companyInfo.getName())
            .addValue("foundation_year", companyInfo.getFoundationYear())
            .addValue("num_employees", companyInfo.getNumEmployees());

        namedParameterJdbcTemplate.update(
            "insert into company_info(partner_id, name, foundation_year, num_employees) " +
                "values(:id, :name, :foundation_year, :num_employees)",
            params
        );
      }

    } catch (Exception e) {
      throw new PersistenceException(ErrorCode.INTERNAL_ERROR, e);
    }

    return id;
  }

  @Override
  public boolean update(PartnerInfo value) throws PersistenceException {
    boolean result = false;

    try {
      SqlParameterSource params = new MapSqlParameterSource().addValue("id", value.getId());
      final Integer total = namedParameterJdbcTemplate.queryForObject(
          "select count(*)::int4 as total from partner_info where id = :id",
          params,
          Integer.class
      );

      if (total > 0) {
        // update address
        params = new MapSqlParameterSource()
            .addValue("id", value.getId())
            .addValue("city", value.getAddressInfo().getCity())
            .addValue("street", value.getAddressInfo().getStreet())
            .addValue("house_number", value.getAddressInfo().getHouseNumber())
            .addValue("inx", value.getAddressInfo().getIdx());

        namedParameterJdbcTemplate.update(
            "update address a set " +
                "city = coalesce(:city, a.city), " +
                "street = coalesce(:street, a.street), " +
                "house_number = coalesce(:house_number, a.house_number), " +
                "inx = coalesce(:inx, a.inx) " +
                "where a.partner_id = :id",
            params
        );

        // update contact
        params = new MapSqlParameterSource()
            .addValue("id", value.getId())
            .addValue("email", value.getContactInfo().getEmail())
            .addValue("phone", value.getContactInfo().getPhone());

        namedParameterJdbcTemplate.update(
            "update contact c set " +
                "email = coalesce(:email, c.email), " +
                "phone = coalesce(:phone, c.phone) " +
                "where c.partner_id = :id",
            params
        );

        if (value.getPartnerType() == PartnerType.naturalPerson) {
          // updatePersonalInfo
          final PersonalInfo personalInfo = value.getPersonalInfo();

          params = new MapSqlParameterSource()
              .addValue("id", value.getId())
              .addValue("first_name", personalInfo.getFirstName())
              .addValue("last_name", personalInfo.getLastName())
              .addValue("middle_name", personalInfo.getMiddleName())
              .addValue("birth_date", personalInfo.getBirthDate())
              .addValue("gender", personalInfo.getGender(), Types.OTHER);

          namedParameterJdbcTemplate.update(
              "update personal_info pi set " +
                  "first_name = coalesce(:first_name, pi.first_name), " +
                  "last_name = coalesce(:last_name, pi.last_name), " +
                  "middle_name = coalesce(:middle_name, pi.middle_name), " +
                  "birth_date = coalesce(:birth_date, pi.birth_date), " +
                  "gender = coalesce(:gender, pi.gender) " +
                  "where pi.partner_id = :id",
              params
          );
        }

        if (value.getPartnerType() == PartnerType.legalEntity) {

          // updateCompanyInfo
          final CompanyInfo companyInfo = value.getCompanyInfo();

          params = new MapSqlParameterSource()
              .addValue("id", value.getId())
              .addValue("name", companyInfo.getName())
              .addValue("foundation_year", companyInfo.getFoundationYear())
              .addValue("num_employees", companyInfo.getNumEmployees());

          namedParameterJdbcTemplate.update(
              "update company_info ci set " +
                  "name = coalesce(:name, ci.name), " +
                  "foundation_year = coalesce(:foundation_year, ci.foundation_year), " +
                  "num_employees = coalesce(:num_employees, ci.num_employees), " +
                  "where ci.partner_id = :id",
              params
          );

        }

        result = true;
      }

    } catch (Exception e) {
      throw new PersistenceException(ErrorCode.INTERNAL_ERROR, e);
    }

    return result;
  }

  @Override
  public boolean remove(UUID id) throws PersistenceException {
    boolean isDeleted = false;
    try {
      final SqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
      final int rowCount = namedParameterJdbcTemplate.update(
          "delete from partner_info where id = :id",
          params
      );
      isDeleted = rowCount > 0;
    } catch (Exception e) {
      throw new PersistenceException(ErrorCode.INTERNAL_ERROR, e);
    }

    return isDeleted;
  }

  @Override
  public @Nullable PartnerInfo find(PartnerSearchParams partnerSearchParams) throws PersistenceException {
    PartnerInfo result;

    try {
      final SqlParameterSource params = new MapSqlParameterSource().addValue("id", partnerSearchParams.getId());

      result = namedParameterJdbcTemplate.queryForObject(
          "select * from v_partners where id = :id",
          params,
          new PartnerRowMapper()
      );
    }
    catch (EmptyResultDataAccessException e) {
      result = null;
    }
    catch (Exception e) {
      throw new PersistenceException(ErrorCode.INTERNAL_ERROR, e);
    }

    return result;
  }

  @Override
  public PageData<PartnerInfo> findAll(
      int pageNum,
      int pageSize,
      @Nullable PartnerSearchParams partnerSearchParams,
      @Nullable SortField<PartnerSortableFields> sort) throws PersistenceException {
    final PageData<PartnerInfo> result = new PageData<>();
    result.setPageNum(pageNum);
    result.setPageSize(pageSize);

    try {
      String searchString = "";
      if (
          partnerSearchParams != null &&
              partnerSearchParams.getSearchQuery() != null &&
              !partnerSearchParams.getSearchQuery().isEmpty()) {
        searchString = "%" + partnerSearchParams.getSearchQuery().toLowerCase() + "%";
      }

      String field = "";
      if (sort != null && sort.getField() != null) {
        field = sort.getField().toString();
      }

      String sortType = "";
      if (sort != null && sort.getSortType() != null) {
        sortType = sort.getSortType().toString();
      }

      // prepare partners
      SqlParameterSource params = new MapSqlParameterSource()
          .addValue("searchString", searchString)
          .addValue("field", field)
          .addValue("sortType", sortType);

      namedParameterJdbcTemplate.update(
          "create temp table t_partners as " +
              "with pp as (" +
              "select p.*, lower(concat(" +
              "p.address_city, " +
              "p.address_street, " +
              "p.address_house_number, " +
              "p.contact_email, " +
              "p.personal_first_name, " +
              "p.personal_last_name, " +
              "p.personal_middle_name, " +
              "p.company_name" +
              ")) as search, " +
              "concat_ws(' ', p.address_street, p.address_house_number, p.address_inx) as address_full, " +
              "case when p.partner_type = 'naturalPerson' " +
              "then concat_ws(' ', p.personal_last_name, p.personal_first_name, p.personal_middle_name) " +
              "else p.company_name " +
              "end as full_name " +
              "from v_partners p) " +

              "select pp.* from pp " +
              "where :searchString = '' or pp.search like :searchString " +
              "order by " +
              "case when :sortType = 'desc' then (" +
              "case when :field = 'phone' then pp.contact_phone end, " +
              "case when :field = 'email' then pp.contact_email end, " +
              "case when :field = 'partner_type' then pp.partner_type end, " +
              "case when :field = 'city' then pp.address_city end, " +
              "case when :field = 'address' then pp.address_full end, " +
              "case when :field = 'name' then pp.full_name end" +
              ") end desc," +
              "case when (:sortType = 'asc' or :sortType = '') then (" +
              "case when :field = 'phone' then pp.contact_phone end, " +
              "case when :field = 'email' then pp.contact_email end, " +
              "case when :field = 'partner_type' then pp.partner_type end, " +
              "case when :field = 'city' then pp.address_city end, " +
              "case when :field = 'address' then pp.address_full end, " +
              "case when :field = 'name' then pp.full_name end" +
              ") end;",
          params
      );

      final Integer totalRows = namedParameterJdbcTemplate.queryForObject(
          "select count(*)::int4 as total from t_partners;",
          new MapSqlParameterSource(),
          Integer.class
      );

      params = new MapSqlParameterSource()
          .addValue("pageSize", pageSize)
          .addValue("offset", pageNum * pageSize);

      final List<PartnerInfo> partners = namedParameterJdbcTemplate.query(
          "select p.* from t_partners p limit :pageSize offset :offset",
          params,
          new PartnerRowMapper()
      );

      int pagesCount = Math.floorDiv(totalRows, pageSize);
      if (totalRows % pageSize != 0) {
        pagesCount++;
      }

      namedParameterJdbcTemplate.update("drop table t_partners", new MapSqlParameterSource());

      result.setTotal(totalRows);
      result.setData(partners);
      result.setPagesCount(pagesCount);

    } catch (Exception e) {
      throw new PersistenceException(ErrorCode.INTERNAL_ERROR, e);
    }

    return result;
  }

  @Override
  public PageData<PartnerInfo> findAll(int pageNum, int pageSize, @Nullable PartnerSearchParams partnerSearchParams) throws PersistenceException {
    return findAll(pageNum, pageSize, partnerSearchParams, null);
  }

  @Override
  public PageData<PartnerInfo> findAll(int pageNum, int pageSize) throws PersistenceException {
    return findAll(pageNum, pageSize, null);
  }

  private static final class PartnerRowMapper implements RowMapper<PartnerInfo> {

    @Nullable
    @Override
    public PartnerInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
      final UUID id = rs.getObject("id", UUID.class);

      if (id == null) {
        return null;
      }

      final PartnerType partnerType = PartnerType.valueOf(rs.getString("partner_type"));

      final Address address = new Address(
          rs.getString("address_city"),
          rs.getString("address_street"),
          rs.getString("address_house_number"),
          rs.getString("address_inx")
      );

      final ContactInfo contactInfo = new ContactInfo(
          rs.getString("contact_phone"),
          rs.getString("contact_email")
      );

      if (partnerType == PartnerType.naturalPerson) {
        final PersonalInfo personalInfo = new PersonalInfo(
            rs.getString("personal_first_name"),
            rs.getString("personal_last_name"),
            rs.getString("personal_middle_name"),
            rs.getDate("personal_birth_date"),
            Gender.valueOf(rs.getString("personal_gender"))
        );

        return new PartnerInfo(address, contactInfo, personalInfo, id);

      } else {
        final CompanyInfo companyInfo = new CompanyInfo(
            rs.getString("company_name"),
            rs.getInt("company_foundation_year"),
            rs.getInt("company_num_employees")
        );

        return new PartnerInfo(address, contactInfo, companyInfo, id);
      }
    }
  }
}
