package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

public class ExposeBinding {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            BrowserType webkit = playwright.webkit();
            Browser browser = webkit.launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            context.exposeBinding("pageURL", (source, args1) -> source.page().url());
            Page page = context.newPage();
//            page.onre

            page.navigate("https://cn.bing.com/translator/?ref=TThis&text=&from=&to=zh-Hans");
            Thread.sleep(10000);
            page.setContent("<script>\n" +
                    "  async function onClick() {\n" +
                    "    document.querySelector('div').textContent = await window.pageURL();\n" +
                    "  }\n" +
                    "</script>\n" +
                    "<button onclick=\"onClick()\">Click me</button>\n" +
                    "<div></div>");
            page.getByRole(AriaRole.BUTTON).click();
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}