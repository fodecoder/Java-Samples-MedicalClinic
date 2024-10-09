package clinic;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class MyCollector implements Collector<Doctor, List<Doctor>, List<Doctor>> {

	@Override
	public Supplier<List<Doctor>> supplier() {
		return LinkedList<Doctor>::new;
	}

	@Override
	public BiConsumer<List<Doctor>, Doctor> accumulator() {
		return new BiConsumer<List<Doctor>, Doctor>() {

			@Override
			public void accept(List<Doctor> t, Doctor u) {

				t.add(u);

			}

		};
	}

	@Override
	public BinaryOperator<List<Doctor>> combiner() {
		return new BinaryOperator<List<Doctor>>() {

			@Override
			public List<Doctor> apply(List<Doctor> t, List<Doctor> u) {

				t.addAll(u);

				return t;
			}

		};
	}

	@Override
	public Function<List<Doctor>, List<Doctor>> finisher() {
		return Function.identity();
	}

	@Override
	public Set<java.util.stream.Collector.Characteristics> characteristics() {
		// TODO Auto-generated method stub
		return null;
	}

}
