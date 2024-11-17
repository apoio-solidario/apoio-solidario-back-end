package com.github.apoioSolidario.config;

import com.github.apoioSolidario.domain.dto.request.ImageRequest;
import com.github.apoioSolidario.domain.dto.response.ImageResponse;
import com.github.apoioSolidario.domain.model.Image;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.print.attribute.standard.Destination;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
    return new ModelMapper();
    }

}
