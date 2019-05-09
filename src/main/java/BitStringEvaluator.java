import org.uncommons.maths.binary.BitString;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;

/**
 * Created on April, 2019
 *
 * @author yagiz
 */
public class BitStringEvaluator implements FitnessEvaluator<BitString> {

	private BitString targetBits;
	private final boolean [] BIT_PATTERN = {true, true, true, false, false, false};
	private int bitSize;

	public BitStringEvaluator(int bitSize) {
		this.bitSize = bitSize;
		targetBits = new BitString(bitSize);
		for (int i = 0; i < bitSize; i++) {
			//Selecting indexes via mod6.
			int bitPatternIndex = i%6;
			targetBits.setBit(i, BIT_PATTERN[bitPatternIndex]);
		}
	}

	public double getFitness(BitString candidate,
			List<? extends BitString> population)
	{
		//Temporary variable for keeping number of matching bits.
		int fitnessCount=0;
		for (int i = 0; i < bitSize; i++) {
			if (targetBits.getBit(i) == candidate.getBit(i)) {
				fitnessCount++;
			}
		}
		return fitnessCount;
	}


	/**
	 * Always returns true.  A higher score indicates a fitter individual.
	 * @return True.
	 */
	public boolean isNatural()
	{
		return true;
	}

}
