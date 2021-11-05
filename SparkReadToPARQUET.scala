import org.apache.spark.sql.{SaveMode, SparkSession}

object SparkReadToPARQUET extends App {

    val spark: SparkSession = SparkSession.builder()
    .master("local[1]")
    .appName("gruppe2")
    .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val dataframe = spark.read.options(Map("inferSchema"->"true","delimiter"->",","header"->"true"))
        .csv("res/happiness.csv")
    dataframe.show()
    dataframe.printSchema()

    dataframe.write.parquet("parquet/happiness.parquet")
    

    
}