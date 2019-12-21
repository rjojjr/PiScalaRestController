package com.kirchnersolutions.pi.scala.rest.models

import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport
import io.circe.generic.AutoDerivation

trait RestObject extends AutoDerivation with ErrorAccumulatingCirceSupport
