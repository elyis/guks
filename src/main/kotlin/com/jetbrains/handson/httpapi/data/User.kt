import kotlinx.serialization.Serializable

@Serializable
data class User(val login:String, var password: String, var mail: String? = "",var name_image: String? = "unknown.svg")

@Serializable
data class UpdateMail(val login: String, val mail: String)

@Serializable
data class OpenUserInformation(val login:String,var mail: String?,var name_image: String?)

@Serializable
data class RespondUser(var login: String = "", var token: String = "", var mail:String = "", var name_icon: String = "")

val users = mutableListOf<User>(
    User("root","toor", "rootMail", "unknown.svg"),
)

val accounts = mutableListOf<OpenUserInformation>()

