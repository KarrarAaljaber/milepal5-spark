import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._

object SparkLoadPARQUETAGG extends App {

    val spark: SparkSession = SparkSession.builder()
    .master("local[1]")
    .appName("gruppe2")
    .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val dataframe = spark.read.format("parquet").load("parquet/happiness.parquet")   

    //top 10 GDP land
    val gdpTop = dataframe.sort(col("GDPpercapita").desc).limit(10)
    gdpTop.write.format("csv").option("header", true).save("csvAGG/gdpTop10.csv")
    //top 10 mest lykkelig land
    val scoreTop = dataframe.sort(col("Score").desc).limit(10)
    scoreTop.write.format("csv").option("header", true).save("csvAGG/ScoreTop10.csv")


}