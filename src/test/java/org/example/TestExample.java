package org.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestExample {
    // Shared between all tests in this class.
    static Playwright playwright;
    static Browser browser;

    // New instance for each test method.
    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        System.out.println("beforeAll");
        playwright = Playwright.create();
        BrowserType.LaunchOptions option = new BrowserType.LaunchOptions();
        option.setHeadless(false);
        browser = playwright.chromium().launch(option);
    }

    @AfterAll
    static void closeBrowser() {
        System.out.println("AfterAll");
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        System.out.println("BeforeEach");
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        System.out.println("AfterEach");
        context.close();
    }

    @Test
    void shouldClickButton() {
        System.out.println("shouldClickButton");
        page.navigate("data:text/html,<script>var result;</script><button onclick='result=\"Clicked\"'>Go</button>");
        page.locator("button").click();
        assertEquals("Clicked", page.evaluate("result"));
    }

    @Test
    void shouldCheckTheBox() {
        page.setContent("<input id='checkbox' type='checkbox'></input>");
        page.locator("input").check();
        assertTrue((Boolean) page.evaluate("() => window['checkbox'].checked"));
    }

//    @Test
//    void shouldSearchWiki() {
//        page.navigate("https://www.wikipedia.org/");
//        page.locator("input[name=\"search\"]").click();
//        page.locator("input[name=\"search\"]").fill("playwright");
//        page.locator("input[name=\"search\"]").press("Enter");
//        assertEquals("https://en.wikipedia.org/wiki/Playwright", page.url());
//    }
}