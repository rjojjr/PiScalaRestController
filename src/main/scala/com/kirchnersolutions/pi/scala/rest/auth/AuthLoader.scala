package com.kirchnersolutions.pi.scala.rest.auth

import java.io.File
import java.util.Base64

import com.kirchnersolutions.utilities.ByteTools

object AuthLoader {

  val path = "auth/auth.key"
  var key: String = null

  def load = {
    key = new String(ByteTools.readBytesFromFile(new File(path)), "UTF-8")
  }

}
