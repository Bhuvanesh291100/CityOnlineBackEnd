package com.city.online.api.dto.request;

import com.city.online.api.model.pojo.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BusinessCategoryCreateRequestDto{
    @NotBlank
    @NotEmpty
    @NotNull
    private Category mainCategory;

    @NotBlank
    @NotEmpty
    @NotNull
    private List<Category> subCategoryList = new ArrayList<>();
}
