package seedu.weme.ui;

import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;

import seedu.weme.model.ReadOnlyWeme;
import seedu.weme.model.meme.Meme;
import seedu.weme.statistics.TagWithCount;
import seedu.weme.statistics.TagWithLike;

/**
 * Panel containing the statistical data about memes in Weme.
 */
public class StatsPanel extends UiPart<Region> {
    private static final String FXML = "StatsPanel.fxml";
    private static final String COUNT_CHART_TITLE = "Tag usage";
    private static final String LIKE_CHART_TITLE = "Likes per Tag";
    private static final int LABEL_LINE_LENGTH = 10;

    @FXML
    private PieChart tagCountChart;
    @FXML
    private PieChart tagLikeChart;

    public StatsPanel(ReadOnlyWeme weme) {
        super(FXML);
        renderCharts(weme);
        weme.getMemeList().addListener((ListChangeListener<Meme>) change -> renderCharts(weme));
    }

    private void renderCharts(ReadOnlyWeme weme) {
        generateTagCountChart(weme);
        generateTagLikeChart(weme);
    }

    /**
     * Generates a {@code PieChart} with a given {@code MemeBook} and {@code Stats}.
     *
     * <p>Styling is mainly done in the CSS file.</p>
     */
    private void generateTagCountChart(ReadOnlyWeme weme) {
        List<TagWithCount> tagsWithCount = weme.getTagsWithCountList();
        ObservableList<PieChart.Data> pieChartData =
                tagsWithCount.stream()
                        .map(tagWithCount -> new PieChart.Data(tagWithCount.getTag().tagName, tagWithCount.getCount()))
                        .map(data -> bindValueToLabel(data))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
        tagCountChart.setData(pieChartData);
        tagCountChart.setTitle(COUNT_CHART_TITLE);
        tagCountChart.setLabelLineLength(LABEL_LINE_LENGTH);
        tagCountChart.setLegendSide(Side.LEFT);
    }

    private void generateTagLikeChart(ReadOnlyWeme weme) {
        List<TagWithLike> tagsWithLikeCountList = weme.getTagsWithLikeCountList();
        System.out.println(tagsWithLikeCountList);
        ObservableList<PieChart.Data> pieChartData =
                tagsWithLikeCountList.stream()
                        .map(tagWithLike -> new PieChart.Data(tagWithLike.getTag().tagName, tagWithLike.getLike()))
                        .map(data -> bindValueToLabel(data))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
        tagLikeChart.setData(pieChartData);
        tagLikeChart.setTitle(LIKE_CHART_TITLE);
        tagLikeChart.setLabelLineLength(LABEL_LINE_LENGTH);
        tagLikeChart.setLegendSide(Side.LEFT);
    }

    private PieChart.Data bindValueToLabel(PieChart.Data data) {
        data.nameProperty().bind(Bindings.concat(data.getName(), ": ", Math.round(data.getPieValue())));
        return data;
    }

}
