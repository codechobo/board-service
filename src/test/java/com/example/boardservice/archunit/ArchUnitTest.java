package com.example.boardservice.archunit;


import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.example.boardservice")
public class ArchUnitTest {

    @ArchTest
    public ArchRule serviceRule = classes()
            .that().resideInAnyPackage("..service..")
            .should().onlyBeAccessed().byAnyPackage("..web..", "..service..");

    @ArchTest
    public ArchRule repositoryRule = classes()
            .that().resideInAnyPackage("..repository..")
            .should().onlyBeAccessed().byAnyPackage("..web..", "..service..", "..repository");

    @ArchTest
    public ArchRule domainDependencyRule = noClasses().that()
            .resideInAnyPackage("..domain..")
            .should().dependOnClassesThat().resideInAnyPackage("..web..", "..service..");

}
