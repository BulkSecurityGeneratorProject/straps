package com.neowave.promaly.cucumber.stepdefs;

import com.neowave.promaly.PromalyV5App;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = PromalyV5App.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
