package com.education.jcc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import net.payrdr.mobile.payment.sdk.SDKPayment
import net.payrdr.mobile.payment.sdk.core.model.ExpiryDate
import net.payrdr.mobile.payment.sdk.exceptions.*
import net.payrdr.mobile.payment.sdk.form.*

import net.payrdr.mobile.payment.sdk.form.gpay.AllowedPaymentMethods
import net.payrdr.mobile.payment.sdk.form.gpay.GooglePayAuthMethod
import net.payrdr.mobile.payment.sdk.form.gpay.GooglePayCardNetwork
import net.payrdr.mobile.payment.sdk.form.gpay.GooglePayCheckoutOption
import net.payrdr.mobile.payment.sdk.form.gpay.GooglePayPaymentDataRequest
import net.payrdr.mobile.payment.sdk.form.gpay.GooglePayPaymentMethod
import net.payrdr.mobile.payment.sdk.form.gpay.GooglePayTotalPriceStatus
import net.payrdr.mobile.payment.sdk.form.gpay.GooglePayUtils
import net.payrdr.mobile.payment.sdk.form.gpay.GoogleTokenizationSpecificationType
import net.payrdr.mobile.payment.sdk.form.gpay.MerchantInfo
import net.payrdr.mobile.payment.sdk.form.gpay.PaymentMethodParameters
import net.payrdr.mobile.payment.sdk.form.gpay.TokenizationSpecification
import net.payrdr.mobile.payment.sdk.form.gpay.TokenizationSpecificationParameters
import net.payrdr.mobile.payment.sdk.form.gpay.TransactionInfo
import net.payrdr.mobile.payment.sdk.form.model.CameraScannerOptions
import net.payrdr.mobile.payment.sdk.form.model.Card
import net.payrdr.mobile.payment.sdk.form.model.CardSaveOptions
import net.payrdr.mobile.payment.sdk.form.model.CryptogramData
import net.payrdr.mobile.payment.sdk.form.model.GooglePayPaymentConfig
import net.payrdr.mobile.payment.sdk.form.model.HolderInputOptions
import net.payrdr.mobile.payment.sdk.form.model.NfcScannerOptions
import net.payrdr.mobile.payment.sdk.form.model.PaymentInfoBindCard
import net.payrdr.mobile.payment.sdk.form.model.PaymentInfoGooglePay
import net.payrdr.mobile.payment.sdk.form.model.PaymentInfoNewCard
import net.payrdr.mobile.payment.sdk.form.model.Theme
import net.payrdr.mobile.payment.sdk.form.ui.helper.Locales.english
import net.payrdr.mobile.payment.sdk.form.ui.helper.Locales.french
import net.payrdr.mobile.payment.sdk.form.ui.helper.Locales.german
import net.payrdr.mobile.payment.sdk.form.ui.helper.Locales.russian
import net.payrdr.mobile.payment.sdk.form.ui.helper.Locales.spanish
import net.payrdr.mobile.payment.sdk.form.ui.helper.Locales.ukrainian
import net.payrdr.mobile.payment.sdk.form.model.CardDeleteOptions
import net.payrdr.mobile.payment.sdk.payment.model.PaymentData
import net.payrdr.mobile.payment.sdk.payment.model.SDKPaymentConfig

class MainActivity : AppCompatActivity() {
    val order = "094aea81-732c-7b3d-9eb2-a10600c39e20"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dsRoot = """
        MIICDTCCAbOgAwIBAgIUOO3a573khC9kCsQJGKj/PpKOSl8wCgYIKoZIzj0EA
        wIwXDELMAkGA1UEBhMCQVUxEzARBgNVBAgMClNvbWUtU3RhdGUxITAfBgNVBA
        oMGEludGVybmV0IFdpZGdpdHMgUHR5IEx0ZDEVMBMGA1UEAwwMZHVtbXkzZHN
        yb290MB4XDTIxMDkxNDA2NDQ1OVoXDTMxMDkxMjA2NDQ1OVowXDELMAkGA1UE
        BhMCQVUxEzARBgNVBAgMClNvbWUtU3RhdGUxITAfBgNVBAoMGEludGVybmV0I
        FdpZGdpdHMgUHR5IEx0ZDEVMBMGA1UEAwwMZHVtbXkzZHNyb290MFkwEwYHKo
        ZIzj0CAQYIKoZIzj0DAQcDQgAE//e+MhwdgWxkFpexkjBCx8FtJ24KznHRXMS
        WabTrRYwdSZMScgwdpG1QvDO/ErTtW8IwouvDRlR2ViheGr02bqNTMFEwHQYD
        VR0OBBYEFHK/QzMXw3kW9UzY5w9LVOXr+6YpMB8GA1UdIwQYMBaAFHK/QzMXw
        3kW9UzY5w9LVOXr+6YpMA8GA1UdEwEB/wQFMAMBAf8wCgYIKoZIzj0EAwIDSA
        AwRQIhAOPEiotH3HJPIjlrj9/0m3BjlgvME0EhGn+pBzoX7Z3LAiAOtAFtkip
        d9T5c9qwFAqpjqwS9sSm5odIzk7ug8wow4Q==
        """
            /* spellchecker: enable */
            .replace("\n", "")
            .trimIndent()
        val paymentConfig =
            SDKPaymentConfig(
                keyProviderUrl = "https://gateway-test.jcc.com.cy/payment/se/keys.do",
                baseURL = "https://gateway-test.jcc.com.cy/payment",
                dsRoot = dsRoot
            )
        SDKPayment.init(paymentConfig)
// A link to an activity or a fragment is required. Order number is required.
        SDKPayment.checkout(activity = this, order)


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Processing the result.

        SDKPayment.handleCheckoutResult(requestCode, data, object :
            ResultPaymentCallback<PaymentData> {
            override fun onSuccess(result: PaymentData) {
                // Order payment result.
                val a=0;
             //   log("PaymentData(${result.mdOrder}, ${result.status})")
            }

            override fun onFail(e: SDKException) {
                // An error occurred.
                when (e) {
                    is SDKAlreadyPaymentException -> makeToast(ERROR_ALREADY_DEPOSITED_ORDER)
                    is SDKCryptogramException -> makeToast(ERROR_CRYPTOGRAM_CANCELED)
                    is SDKDeclinedException -> makeToast(ERROR_DECLINED_ORDER)
                    is SDKPaymentApiException -> makeToast(ERROR_PAYMENT_API)
                    is SDKTransactionException -> makeToast(ERROR_WORK_CREATE_TRANSACTION)
                    is SDKOrderNotExistException -> makeToast(ERROR_ORDER_NOT_EXIT_API)
                    is SDKNotConfigureException -> makeToast(ERROR_NOT_CONFIGURE_EXCEPTION)
                    else -> log("${e.message} ${e.cause}")
                }
            }
        })

    }


    private fun makeToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val ERROR_WORK_CREATE_TRANSACTION: String = "Exception: while create transaction with EC or RSA"
        private const val ERROR_ALREADY_DEPOSITED_ORDER: String = "Exception: the order has already been deposited"
        private const val ERROR_DECLINED_ORDER: String = "Exception: the order has been declined"
        private const val ERROR_CRYPTOGRAM_CANCELED: String = "Exception: the cryptogram creation has been canceled or some error"
        private const val ERROR_PAYMENT_API: String = "Exception: the api work problem"
        private const val ERROR_ORDER_NOT_EXIT_API: String = "Exception: the order not exist"
        private const val ERROR_NOT_CONFIGURE_EXCEPTION: String =
            "Merchant is not configured to be used without 3DS2SDK"
    }
}