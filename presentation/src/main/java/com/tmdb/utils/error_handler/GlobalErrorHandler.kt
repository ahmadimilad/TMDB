package com.tmdb.utils.error_handler

import android.content.Context
import com.tmdb.R
import com.tmdb.utils.error_handler.exceptions.AppException
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Retrofit
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class GlobalErrorHandler @Inject constructor(
    private var retrofit: Retrofit,
    @ApplicationContext private var context: Context
) {
    fun getErrorMessage(throwable: Throwable): String {
        return getErrorModel(throwable).statusMessage
    }

    fun getErrorCode(throwable: Throwable): Int {
        return getErrorModel(throwable).statusCode ?: -1
    }

    private fun getErrorModel(throwable: Throwable): ErrorModel {
        Timber.e("MyTag-GlobalErrorHandler-> $throwable")

        return when (throwable) {
            is HttpException -> {
                try { //400, 401, 403, 404, 409, 422, 500, 501, 502, 503 , ...
                    parseRetrofitError(throwable.response()!!.errorBody()!!)!!
                } catch (e: Exception) {
                    ErrorModel(throwable = throwable, statusMessage = context.getString(R.string.unknown_exception))
                }
            }
            is IOException -> {
                //Connect timed out + Network is unreachable
                ErrorModel(throwable, statusMessage = context.getString(R.string.host_exception))
            }
            is AppException -> {
                //CustomException -> Internet connection error
                when (throwable.error) {
                    is String -> {
                        ErrorModel(throwable, statusMessage = throwable.error)
                    }
                    is Int -> {
                        ErrorModel(throwable, statusMessage = context.getString(throwable.error))
                    }
                    else -> {
                        ErrorModel(throwable, statusMessage = context.getString(R.string.unknown_exception))
                    }
                }

            }
            else -> {
                throwable.message
                    ?.let { ErrorModel(throwable, statusMessage = it) }
                    ?: ErrorModel(throwable, statusMessage = context.getString(R.string.unknown_exception))
            }
        }
    }

    private fun parseRetrofitError(response: ResponseBody): ErrorModel? {
        val converter: Converter<ResponseBody, ErrorModel> = retrofit.responseBodyConverter(ErrorModel::class.java, arrayOfNulls<Annotation>(0))
        return converter.convert(response)
    }

    fun forceCrash() {
        throw RuntimeException()
    }
}