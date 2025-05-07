package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class App {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            BrowserType.LaunchOptions opt = new BrowserType.LaunchOptions();
            opt.setHeadless(false);

            Browser browser = playwright.chromium().launch(opt);
            Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
            contextOptions.setViewportSize(null);
            BrowserContext context = browser.newContext(contextOptions);
            Page page = context.newPage();

            Page.NavigateOptions navigateOptions = new Page.NavigateOptions();
            navigateOptions.setTimeout(600000);
            page.navigate("http://playwright.dev",navigateOptions);
            System.out.println("title=="+page.title());
            Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
            screenshotOptions.setPath(Paths.get("src/main/java/screenshots/screenshot.png"));
            screenshotOptions.setFullPage(true);
            page.screenshot(screenshotOptions);
            // Expect a title "to contain" a substring.
            assertThat(page).hasTitle(Pattern.compile("Fast and reliable end-to-end testing for modern web apps | Playwright"));

            // create a locator
            Locator getStarted = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Get Started"));
            Locator.ScreenshotOptions getStartedScreenshotOptions = new Locator.ScreenshotOptions();
            getStartedScreenshotOptions.setPath(Paths.get("src/main/java/screenshots/getStartedScreenshot.png"));
            getStarted.screenshot(getStartedScreenshotOptions);
            // Expect an attribute "to be strictly equal" to the value.
            assertThat(getStarted).hasAttribute("href", "/docs/intro");

            // Click the get started link.
            getStarted.click();

            // Expects page to have a heading with the name of Installation.
            assertThat(page.getByRole(AriaRole.HEADING,
                    new Page.GetByRoleOptions().setName("Installation"))).isVisible();
        }
    }
}