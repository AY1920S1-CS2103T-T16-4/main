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

import seedu.weme.model.ReadOnlyMemeBook;
import seedu.weme.model.meme.Meme;
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
        generatePieChart(memeBook, stats);
        memeBook.getMemeList().addListener((ListChangeListener<Meme>) change -> generatePieChart(memeBook, stats));
    }

    /**
     * Generates a {@code PieChart} with a given {@code MemeBook} and {@code Stats}.
     *
     * <p>Styling is mainly done in the CSS file.</p>
     */
    private void generatePieChart(ReadOnlyMemeBook memeBook, Stats stats) {
        stats.parseMemeBookForTags(memeBook);
        List<TagWithCount> tagsWithCount = stats.getTagsWithCountList();
        ObservableList<PieChart.Data> pieChartData =
                tagsWithCount.stream()
                        .map(tagWithCount -> new PieChart.Data(tagWithCount.getTag().tagName, tagWithCount.getCount()))
                        .map(data -> {
                                    data.nameProperty().bind(
                                            Bindings.concat(
                                                    data.getName(), ": ", Math.round(data.getPieValue())
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
