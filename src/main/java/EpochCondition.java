import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.framework.TerminationCondition;

/**
 * Created on April, 2019
 *
 * @author yagiz
 */
public class EpochCondition implements TerminationCondition {

	private final int maximumEpoch;

	EpochCondition(int maximumEpoch) {
		this.maximumEpoch = maximumEpoch;
	}

	public boolean shouldTerminate(PopulationData<?> populationData) {
		return populationData.getGenerationNumber() >= maximumEpoch ? true : false;
	}
}
