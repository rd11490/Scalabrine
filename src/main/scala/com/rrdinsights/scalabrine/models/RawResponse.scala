package com.rrdinsights.scalabrine.models

private[rrdinsights] trait RawResponse {
  def resource: String

  def resultSets: Array[ResultSetResponse]
}

private[rrdinsights] case class ResultSetResponse(name: String,
                                             headers: Array[String],
                                             rowSet: Array[Array[Any]])

private[models] trait ResultSetRawResponseConverters {
  protected def converters: Seq[_ <: ResultSetRawResponseConverter[_]]

  protected def converterOf(name: String): ResultSetRawResponseConverter[_] = converterOfOpt(name)
    .getOrElse(throw new IllegalArgumentException(s"$name is not a valid result set response object"))

  protected def converterOfOpt(name: String): Option[ResultSetRawResponseConverter[_]] = converters.find(_.name == name)

  protected def convert[T <: ConvertedResultSetResponse](rawResponses: Seq[ResultSetResponse], converter: ResultSetRawResponseConverter[T]): Seq[T] =
    rawResponses
      .find(_.name == converter.name)
      .map(v => converter.convertRaw(v.rowSet))
      .getOrElse(Seq.empty)



}

private[models] trait ResultSetRawResponseConverter[T <: ConvertedResultSetResponse] {
  def name: String

  def convertRaw(rows: Array[Array[Any]]): Seq[T]
}

private[models] trait ConvertedResultSetResponse