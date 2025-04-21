package com.example.tfg.model.user

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object PassEncryption {
    private const val SECRET_KEY = "1234567890123456" // Debe tener 16 caracteres para AES-128
    private const val INIT_VECTOR = "RandomInitVector" // También 16 caracteres

    fun encrypt(value: String): String {
        val ivParameterSpec = IvParameterSpec(INIT_VECTOR.toByteArray(Charsets.UTF_8))
        val secretKeySpec = SecretKeySpec(SECRET_KEY.toByteArray(Charsets.UTF_8), "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)

        val encrypted = cipher.doFinal(value.toByteArray())
        return Base64.encodeToString(encrypted, Base64.NO_WRAP)
    }
}