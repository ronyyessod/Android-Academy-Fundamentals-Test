import java.math.BigDecimal
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap


class OrdersAnalyzer {

    data class Order(val orderId: Int, val creationDate: LocalDateTime, val orderLines: List<OrderLine>)

    data class OrderLine(val productId: Int, val name: String, val quantity: Int, val unitPrice: BigDecimal)

    fun stringToDate(date: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        return LocalDateTime.parse(date, formatter)
    }

    private fun sumOrdersPrice(orderLines: List<OrderLine>): Int {
        var sum = BigDecimal(0)
        for (line in orderLines) {
            sum += (line.unitPrice * line.quantity.toBigDecimal())
        }
        return sum.toInt()
    }

    private fun sumOrdersQuantity(orderLines: List<OrderLine>): Int {
        var sum = 0
        for (line in orderLines) {
            sum += line.quantity
        }
        return sum
    }

    fun totalDailySales(orders: List<Order>): MutableMap<DayOfWeek, Int> {
        val ordersMap = HashMap<DayOfWeek, Int>()
        for (order in orders) {
            val orderDay = order.creationDate.dayOfWeek
            val orderSum = sumOrdersQuantity(order.orderLines)
            ordersMap[orderDay] = ordersMap.getOrDefault(orderDay, 0) + orderSum
        }
        return ordersMap.toSortedMap()
    }
}


fun main() {
    val analyzer = OrdersAnalyzer()
    val orders = listOf(
        OrdersAnalyzer.Order(
            554,
            analyzer.stringToDate("2017-03-25T10:35:20"), // Saturday
            listOf(
                OrdersAnalyzer.OrderLine(
                9872,
                "Pencil",
                3,
                BigDecimal(3.12)
                )
            )
        ),
        OrdersAnalyzer.Order(
            555,
            analyzer.stringToDate("2017-03-25T11:24:20"), // Saturday
            listOf(
                OrdersAnalyzer.OrderLine(
                9872,
                "Pencil",
                2,
                BigDecimal(3.12)
                ),
                OrdersAnalyzer.OrderLine(
                    1746,
                    "Eraser",
                    1,
                    BigDecimal(1.00)
                )
            )
        ),
        OrdersAnalyzer.Order(
            453,
            analyzer.stringToDate("2017-03-27T14:53:12"), // Monday
            listOf(
                OrdersAnalyzer.OrderLine(
                    5723,
                    "Pen",
                    4,
                    BigDecimal(4.22)
                ),
                OrdersAnalyzer.OrderLine(
                    9872,
                    "Pencil",
                    3,
                    BigDecimal(3.12)
                ),
                OrdersAnalyzer.OrderLine(
                    3433,
                    "Erasers Set",
                    1,
                    BigDecimal(6.15)
                )
            )
        ),
        OrdersAnalyzer.Order(
            431,
            analyzer.stringToDate("2017-03-20T12:15:02"), // Monday
            listOf(
                OrdersAnalyzer.OrderLine(
                    5723,
                    "Pen",
                    7,
                    BigDecimal(4.22)
                ),
                OrdersAnalyzer.OrderLine(
                    3433,
                    "Erasers Set",
                    2,
                    BigDecimal(6.15)
                )
            )
        ),
        OrdersAnalyzer.Order(
            690,
            analyzer.stringToDate("2017-03-26T11:14:00"), // Sunday
            listOf(
                OrdersAnalyzer.OrderLine(
                    9872,
                    "Pencil",
                    4,
                    BigDecimal(3.12)
                ),
                OrdersAnalyzer.OrderLine(
                    4089,
                    "Marker",
                    5,
                    BigDecimal(4.50)
                )
            )
        )
    )
    println(analyzer.totalDailySales(orders))
}

