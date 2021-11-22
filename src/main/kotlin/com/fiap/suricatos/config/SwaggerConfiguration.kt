package com.fiap.suricatos.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfiguration {
    @Bean
    fun apiDoc(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fiap.suricatos"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
    }

    private fun metaData(): ApiInfo {
        return ApiInfoBuilder()
                .title("Suricatos API")
                .description("Documentação Api Suricatos" +
                        "\n 1. Para acessar endpoints que iniciam com /api deve ser recuperado o token no /login e ser passado no " +
                        "header Authorization das requisições como Bearer {token}; \n " +
                        "2. para acessar /user (Cadastro de usuário) e /login não precisa passar o Authorization no header;\n " +
                        "3. As requests que passam imagens como form-data deve ser passado o json(body) como form-data do tipo. string " +
                        "Para ver o exemplo da string basta expandir o tópico nesta documentação e terá o exemplo logo abaixo;\n " +
                        "4. As fotos serão retornadas como url da amazon, basta acessar a url parar obter a imagem;")
                .version("1.0")
                .license("Apache license version 2.0")
                .licenseUrl("http://www.apache.org/license/license-2-0")
                .contact(Contact("Nathalia Oliveira", "https://github.com/natholiveira/suricatos", "vo.nathalia@gmail.com"))
                .build()
    }
}