package racingcar.turn

import racingcar.racing.car.Car
import racingcar.racing.rule.CarMovementRule

data class Turn(
    val current: Int = 0,
    val cars: List<Car>,
    val rule: CarMovementRule
) {
    fun doEachTurn(): Turn {
        return copy(
            current = current + 1,
            cars = cars.map { it.moveIf { rule.rule() } },
            rule = rule
        )
    }

    fun nextTurn(): Turn {
        return doEachTurn()
    }

    fun isFinish(limit: Int) = current >= limit
}
