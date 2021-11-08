import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._

object ReadHDFS extends App {

    val spark: SparkSession = SparkSession.builder()
    .master("local[1]")
    .appName("gruppe2")
    .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val rddFromFile = spark.sparkContext.textFile("hdfs://nn1home:8020/happiness.txt")
    val rddWhole = spark.sparkContext.wholeTextFiles("hdfs://nn1home:8020/happiness.txt")


}