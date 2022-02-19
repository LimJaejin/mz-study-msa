package com.lguplus.fleta.config;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.service.AllowableValues;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.EnumTypeDeterminer;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
import springfox.documentation.swagger.schema.ApiModelProperties;

import static com.google.common.base.Strings.emptyToNull;
import static springfox.documentation.swagger.common.SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER;
import static springfox.documentation.swagger.readers.parameter.Examples.examples;

@Slf4j
@RequiredArgsConstructor
@Primary
@Component
public class CustomSwaggerParameterBuilder implements ExpandedParameterBuilderPlugin {

    private final DescriptionResolver descriptions;
    private final EnumTypeDeterminer enumTypeDeterminer;

    /**
     * Implement this method to override the Parameter using ParameterBuilder available in the context
     * @param context - context that can be used to override the parameter attributes
     * @see Parameter
     * @see ParameterBuilder
     */
    @Override
    public void apply(ParameterExpansionContext context) {
        Optional<ApiModelProperty> maybeApiModelProperty = ParameterExpansionContextWrapper.findAnnotation(context, ApiModelProperty.class);
        maybeApiModelProperty.ifPresent(apiModelProperty -> this.fromApiModelProperty(context, apiModelProperty));

        Optional<ApiParam> maybeApiParam = ParameterExpansionContextWrapper.findAnnotation(context, ApiParam.class);
        maybeApiParam.ifPresent(apiParam -> this.fromApiParam(context, apiParam));
    }

    /**
     * Returns if a plugin should be invoked according to the given delimiter.
     * @return if the plugin should be invoked
     */
    @Override
    public boolean supports(DocumentationType delimiter) {
        return SwaggerPluginSupport.pluginDoesApply(delimiter);
    }

    private void fromApiModelProperty(ParameterExpansionContext context, ApiModelProperty apiModelProperty) {
        String allowableProperty = emptyToNull(apiModelProperty.allowableValues());
        AllowableValues allowable = allowableValues(
            Optional.ofNullable(allowableProperty),
            context.getFieldType().getErasedType());

        maybeSetParameterName(context, apiModelProperty.name())
            .description(this.descriptions.resolve(apiModelProperty.value()))
            .required(apiModelProperty.required())
            .allowableValues(allowable)
            .parameterAccess(apiModelProperty.access())
            .hidden(apiModelProperty.hidden())
            .scalarExample(apiModelProperty.example())
            .order(apiModelProperty.position())
            .build();
    }

    private void fromApiParam(ParameterExpansionContext context, ApiParam apiParam) {
        String allowableProperty = emptyToNull(apiParam.allowableValues());
        AllowableValues allowable = allowableValues(
            Optional.ofNullable(allowableProperty),
            context.getFieldType().getErasedType());

        maybeSetParameterName(context, apiParam.name())
            .description(this.descriptions.resolve(apiParam.value()))
            .defaultValue(apiParam.defaultValue())
            .required(apiParam.required())
            .allowMultiple(apiParam.allowMultiple())
            .allowableValues(allowable)
            .parameterAccess(apiParam.access())
            .hidden(apiParam.hidden())
            .scalarExample(apiParam.example())
            .complexExamples(examples(apiParam.examples()))
            .order(SWAGGER_PLUGIN_ORDER)
            .build();
    }

    private ParameterBuilder maybeSetParameterName(ParameterExpansionContext context, String parameterName) {
        if (!Strings.isNullOrEmpty(parameterName)) {
            context.getParameterBuilder().name(parameterName);
        }
        return context.getParameterBuilder();
    }

    private AllowableValues allowableValues(final Optional<String> optionalAllowable, Class<?> fieldType) {
        if (this.enumTypeDeterminer.isEnum(fieldType)) {
            return new AllowableListValues(getEnumValues(fieldType), "LIST");
        }
        return optionalAllowable.map(ApiModelProperties::allowableValueFromString).orElse(null);
    }

    private List<String> getEnumValues(final Class<?> subject) {
        return Lists.transform(Arrays.asList(subject.getEnumConstants()), (Function<Object, String>) Object::toString);
    }
}
