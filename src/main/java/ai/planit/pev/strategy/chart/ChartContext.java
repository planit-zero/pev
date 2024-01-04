package ai.planit.pev.strategy.chart;

import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.core.webclient.PevWebClient;
import ai.planit.pev.core.webclient.PevWebClientUtil;
import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.Chart;
import ai.planit.pev.strategy.chart.object.common.ChartData;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.strategy.chart.object.common.ChartStyleSection;
import ai.planit.pev.utility.PevChartUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class ChartContext {
    private ChartStrategy chartStrategy;

    public Chart.Response getChart(List<ChartElement> format, List<ChartElement> data, List<ChartStyleSection> style, boolean applyStyle) {
        List<ChartElement> elements = new ArrayList<>();

        for (ChartElement f : format) {
            Optional<ChartElement> value = data
                    .stream()
                    .filter(d -> d.getId().equals(f.getId()) && d.getParentId().equals(f.getParentId()))
                    .findFirst();

            value.ifPresentOrElse(elements::add, () -> {
                if (f.getClassType().equals(ChartClassType.VALUE)) f.setContent(null);
                elements.add(f);
            });
        }

        Chart.Response chart = new Chart.Response();
        chart.setData(data);
        chart.setSections(PevChartUtil.getChartSections(elements, style, applyStyle));

        return chart;
    }

    public ChartData getMaskedData(ChartData data) {
        String url = "http://172.26.33.23:28092";
        String uri = "/api/emr/ann-record-sheet";
        WebClient webClient = PevWebClient.getWebClient(url, ErrorType.RID_CONNECTION_TIMEOUT);

        return webClient.post()
                .uri(uri)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(BodyInserters.fromValue(data))
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, response -> PevWebClientUtil.throwServerError(response, ErrorType.ANN_PROCESS_FAILED))
                .onStatus(HttpStatus::is4xxClientError, response -> PevWebClientUtil.throwServerError(response, ErrorType.ANN_PROCESS_FAILED))
                .bodyToMono(ChartData.class)
                .block();
    }
}
