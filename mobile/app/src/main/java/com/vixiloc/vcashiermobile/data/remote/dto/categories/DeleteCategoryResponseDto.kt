package com.vixiloc.vcashiermobile.data.remote.dto.categories

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.OnlyResponseMessage

data class DeleteCategoryResponseDto(

	@field:SerializedName("message")
	val message: String
)

fun DeleteCategoryResponseDto.toOnlyMessageResponseDto() = OnlyResponseMessage(message)