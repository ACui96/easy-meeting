package com.bocft.meeting.controller;

import com.bocft.meeting.common.Result;
import com.bocft.meeting.model.Conference;
import com.bocft.meeting.service.ConferenceService;
import com.bocft.meeting.vo.UserConferenceVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;


/**
 * @author Acui
 * @date 2022年07月08日 17:00
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
@WebMvcTest(AdminReviewController.class)
@Slf4j
public class AdminReviewControllerTest {

    @Autowired
    ConferenceService conferenceService;

    @Autowired
    AdminReviewController adminReviewController;

    @Autowired
    private MockMvc mvc;
}
