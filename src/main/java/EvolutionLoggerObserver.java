import javafx.util.Pair;
import org.jfree.data.xy.XYSeries;

import java.util.*;

/**
 * Created on April, 2019
 *
 * @author yagiz
 */
public class EvolutionLoggerObserver implements Observer {

	private List<Pair<Double,Long>> stdDevAndDurationPairList = new ArrayList<>();
	private XYSeries xySeriesStdDev;

	public EvolutionLoggerObserver() {}

	@Override
	public void update(Observable o, Object arg) {
		if (arg != null) {
			stdDevAndDurationPairList.add((Pair<Double, Long>) arg);
		}
		else {
			setXYSeries();
		}
//		try {
//			List<Pair<Double,Long>> liste = (List<Pair<Double, Long>>) arg;
//			if (arg != null) {
//				stdDevAndDurationPairList.add(liste.get(0));
//				bestAndDurationPairList.add(liste.get(1));
//			}
//			else {
//				setXYSeries();
//			}
//		}catch (Exception e)
//		{
//			System.out.println("Error occured in Observer method.");
//		}

	}

	private void setXYSeries() {
		XYSeries xySeriesStdDev = new XYSeries("Graph");
		stdDevAndDurationPairList.forEach(pair -> xySeriesStdDev.add(pair.getValue(), pair.getKey()));
//		XYSeries xySeriesBests = new XYSeries("Graph");
//		bestAndDurationPairList.forEach(pair -> xySeriesBests.add(pair.getValue(), pair.getKey()));
		this.xySeriesStdDev = xySeriesStdDev;
//		this.xySeriesBests = xySeriesBests;
	}

	public XYSeries getStdDevXySeries() {
		return xySeriesStdDev;
	}

}
