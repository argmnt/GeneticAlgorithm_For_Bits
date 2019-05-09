import javafx.util.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created on April, 2019
 *
 * @author yagiz
 */
public class LineChartEx extends JFrame {



	public LineChartEx(XYSeries xySeries,String chartTitle, String yAxis) {

		initUI(xySeries, chartTitle, yAxis);
	}

	private void initUI(XYSeries xySeries, String chartTitle, String yAxis) {
//		XYDataset dataset = createDataset(pairList);
		XYSeriesCollection dataCollection = new XYSeriesCollection();
		dataCollection.addSeries(xySeries);
		XYDataset dataset = dataCollection;
		JFreeChart chart = createChart(dataset, chartTitle, yAxis);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		add(chartPanel);

		pack();
		setTitle("Line chart");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private XYDataset createDataset(Object pairListObj) {
		ArrayList<Pair<Double,Long>> pairList = (ArrayList<Pair<Double, Long>>) pairListObj;
		XYSeries series = new XYSeries("2016");

		XYSeriesCollection dataset = new XYSeriesCollection();
		pairList.forEach(pair -> series.add(pair.getKey(), pair.getValue()));
		dataset.addSeries(series);

		return dataset;
	}

	private JFreeChart createChart(XYDataset dataset, String chartTitle, String yAxis) {

		JFreeChart chart = ChartFactory.createXYLineChart(
				chartTitle,
				"Time in millis",
				yAxis,
				dataset,
				PlotOrientation.VERTICAL,
				true,
				true,
				false
														 );

		XYPlot plot = chart.getXYPlot();

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));

		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		chart.getLegend().setFrame(BlockBorder.NONE);

		chart.setTitle(new TextTitle(chartTitle,
									 new Font("Serif", java.awt.Font.BOLD, 18)
					   )
					  );

		return chart;

	}

}
