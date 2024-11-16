package com.github.apoioSolidario.domain.dto.mapper;

import com.github.apoioSolidario.domain.dto.request.ImageRequest;
import com.github.apoioSolidario.domain.dto.response.ImageResponse;
import com.github.apoioSolidario.domain.model.Image;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper extends AbstractMapper {
    public ImageMapper(ModelMapper modelMapper) {
        super(modelMapper);
        modelMapper.addMappings(
                new PropertyMap<ImageRequest, Image>() {
                    @Override
                    protected void configure() {
                        map(null,destination.getId());
                        map(source.getImageName(), destination.getImageName());
                        map(source.getImageUrl(), destination.getImageUrl());
                    }
                });

        modelMapper.addMappings(
                new PropertyMap<Image, ImageResponse>() {
                    @Override
                    protected void configure() {
                        map(source.getImageName(), destination.getImageName());
                        map(source.getImageUrl(), destination.getImageUrl());
                        map(source.getOng().getId(),destination.getEntityId());
                    }
                });
    }
}
