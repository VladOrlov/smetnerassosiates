package com.smetnerassosiates.util;

import com.google.gson.Gson;

import com.smetnerassosiates.domain.Theme;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
@Slf4j
public class ThemeParser {

  public List<Theme> parseThemes(@NonNull String url) {
    Document content = getContent(url);
    String tableContent = parseTableContent(content);
    Theme[] themes = new Gson().fromJson(tableContent, Theme[].class);

    return Arrays.asList(themes);
  }

  public Document getContent(@NonNull String url) {

    CloseableHttpClient httpclient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet(url);
    Document document = null;

    try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
      HttpEntity entity = response.getEntity();
      document = Jsoup.parse(EntityUtils.toString(entity, Charset.forName("UTF-8")));
      EntityUtils.consume(entity);
      return document;
    } catch (IOException e) {
      log.error(e.getLocalizedMessage(), e);
    }
    return document;
  }

  private String parseTableContent(@NonNull Document content) throws ParseException {
    Element table = content.getElementById("example1");
    if (table == null) {
      throw new ParseException("Table element not found!");
    }

    List tableHead = getTableHead(table.getElementsByTag("th"));

    StringBuilder writer = new StringBuilder("[");

    Elements rows = table.getElementsByTag("tbody").get(0).getElementsByTag("tr");
    for (int k = 0; k < rows.size(); k++) {
      Elements columns = rows.get(k).getElementsByTag("td");
      writer.append("{ ");
      for (int i = 0; i < columns.size(); i++) {
        writer.append('"').append(tableHead.get(i)).append('"')
            .append(" : ")
            .append('"').append(columns.get(i).text()).append('"')
            .append(i == columns.size() - 1 ? " " : ", ");
      }
      writer.append(k == rows.size() - 1 ? "} " : " }, ");
    }

    return writer.append("]").toString();
  }

  private List getTableHead(@NonNull Elements headElements) {
    List<String> headersList = new ArrayList<>();
    headersList.add("renderingEngine");
    headersList.add("browser");
    headersList.add("platform");
    headersList.add("engineVersion");
    headersList.add("cssGrade");

    return headersList;
  }

}
