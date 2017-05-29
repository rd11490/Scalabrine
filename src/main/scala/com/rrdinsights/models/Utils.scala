package com.rrdinsights.models

private[models] object Utils {
  def trimOrNull(s: String): String = if (s != null) s.trim else s
}
