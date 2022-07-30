package com.dvladir.partners.api;

import com.dvladir.common.api.annotations.ApiInternalError;
import com.dvladir.common.api.annotations.ApiValidationError;
import com.dvladir.partners.api.annotations.ApiPartnerNotFound;
import com.dvladir.common.api.dto.*;
import com.dvladir.common.domain.*;
import com.dvladir.partners.api.dto.*;
import com.dvladir.partners.domain.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import com.dvladir.partners.api.mappers.PartnerMapper;
import com.dvladir.partners.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@RestController
@RequestMapping("partner")
public class PartnerController {

  private final PartnerService partnerService;

  @Autowired
  public PartnerController(PartnerService partnerService) {
    this.partnerService = partnerService;
  }

  @Operation(summary = "Search partners")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Search result", useReturnTypeSchema = true),
  })
  @ApiInternalError
  @GetMapping("search")
  public PageDataDto<PartnerHeaderDto> search(
      @RequestParam(required = false) String query,
      @RequestParam(defaultValue = "0") int pageNum,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(required = false) String sort
      ) {

    final SortFieldDto sortFieldDto = SortFieldDto.parse(sort);

    final SortField<PartnerSortableFields> sortField = sortFieldDto != null ?
        PartnerMapper.INSTANCE.sortFieldDtoToSortField(sortFieldDto) :
        null;

    final PageData<PartnerInfo> searchResult = partnerService.searchPartners(query, pageNum, pageSize, sortField);

    return PartnerMapper.INSTANCE.partnerPageToPartnerHeaderPageDto(searchResult);
  }

  @Operation(description = "Get partner by id")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Partner info", useReturnTypeSchema = true),
  })
  @ApiInternalError
  @ApiPartnerNotFound
  @GetMapping("{partnerId}")
  public PartnerInfoDto getPartner(@PathVariable UUID partnerId) {
    final PartnerInfo partner = partnerService.getPartnerById(partnerId);
    return PartnerMapper.INSTANCE.partnerInfoToPartnerDto(partner);
  }

  @Operation(description = "Create partner")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Created partner ID", useReturnTypeSchema = true),
  })
  @ApiInternalError
  @ApiValidationError
  @PostMapping()
  public IdentifyDto createPartner(@RequestBody PartnerInfoDto partnerInfoDto) {
    final PartnerInfo partner = PartnerMapper.INSTANCE.partnerInfoDtoToPartner(partnerInfoDto);
    final UUID partnerId = partnerService.addPartner(partner);
    return new IdentifyDto(partnerId);
  }

  @Operation(description = "Remove partner")
  @ApiResponses({
      @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
  })
  @ApiInternalError
  @ApiPartnerNotFound
  @DeleteMapping("{partnerId}")
  public void removePartner(@PathVariable UUID partnerId) {
    partnerService.removePartner(partnerId);
  }

  @Operation(description = "Update partner")
  @ApiResponses({
      @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
  })
  @ApiInternalError
  @ApiPartnerNotFound
  @ApiValidationError
  @PutMapping("{partnerId}")
  public void updatePartner(@PathVariable UUID partnerId, @RequestBody PartnerInfoDto partnerInfoDto) {
    final PartnerInfo partner = PartnerMapper.INSTANCE.partnerInfoDtoToPartner(partnerInfoDto);
    partner.setId(partnerId);
    partnerService.updatePartner(partner);
  }

}
