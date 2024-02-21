package com.vk.tlschema

internal object TLSchemaTestingUtil {
    private const val RESOURCES_PATH = "src/test/resources"
    private const val PACKAGE_PATH = "com/vk/tlschema"

    fun getTestDataPath(): String {
        return "$RESOURCES_PATH/$PACKAGE_PATH"
    }
}
