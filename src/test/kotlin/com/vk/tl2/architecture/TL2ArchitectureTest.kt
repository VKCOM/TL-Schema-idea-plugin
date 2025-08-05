package com.vk.tl2.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.ext.list.withPackage
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

class TL2ArchitectureTest {
    private val projectScope = Konsist.scopeFromProject()

    @Test
    fun `TL2 file names must begin with TL2`() {
        projectScope
            .classesAndInterfacesAndObjects(false)
            .withPackage("com.vk.tl2..")
            .assertTrue { it.hasNameStartingWith("TL2") }
    }

    @Test
    fun `TL1 and TL2 must not have dependencies on each others`() {
        projectScope
            .assertArchitecture {
                val tl1 = Layer("TL1", "com.vk.tl2..")
                val tl2 = Layer("TL2", "com.vk.tlschema..")

                listOf(tl1, tl2).dependsOnNothing()
            }
    }
}
