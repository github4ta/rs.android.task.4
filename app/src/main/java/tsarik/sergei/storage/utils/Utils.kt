package tsarik.sergei.storage.utils

class Utils {
    companion object {
        private val charPool : List<Char> = ('a'..'z').toList()

        fun generateString(length: Int): String {
            val randomString = (1..length)
                .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")
                .replaceFirstChar { it.uppercase() };

            return randomString
        }
    }
}