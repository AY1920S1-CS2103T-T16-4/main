package seedu.weme.ui;

import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import seedu.weme.model.ReadOnlyMemeBook;
import seedu.weme.statistics.Stats;
import seedu.weme.statistics.TagWithCount;

/**
 * Panel containing the statistical data about memes in Weme.
 */
public class StatsPanel extends UiPart<Region> {
    private static final String FXML = "StatsPanel.fxml";

    @FXML
    private PieChart piechart;

    public StatsPanel(ReadOnlyMemeBook memeBook, Stats stats) {
        super(FXML);
        stats.parseMemeBookForTags(memeBook);
        List<TagWithCount> tagsWithCount = stats.getTagsWithCountList();
        System.out.println(tagsWithCount);
        ObservableList<PieChart.Data> pieChartData =
                tagsWithCount.stream()
                        .map(tagWithCount -> new PieChart.Data(tagWithCount.getTag().tagName, tagWithCount.getCount()))
                        .map(data -> {
                                    data.nameProperty().bind(
                                            Bindings.concat(
                                                    data.getName(), " ", data.pieValueProperty(), " likes"
                                            )
                                    );
                                    return data;
                                }
                        )
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
        piechart.setData(pieChartData);
        piechart.setTitle("Tags used");
        piechart.setLabelLineLength(10);
        piechart.setLegendSide(Side.LEFT);
    }

}

