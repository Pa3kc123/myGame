package sk.pa3kc.holder

import org.lwjgl.opengl.GL20
import sk.pa3kc.poko.program.ShaderProgram
import sk.pa3kc.util.GLCollection

class ShaderPrograms : GLCollection<ShaderProgram>() {
    var hasActiveProgram = false
        private set
    var activeProgramId: ShaderProgram? = null

    override fun unbind() {
        GL20.glUseProgram(0)
        this.hasActiveProgram = false
        this.activeProgramId = null
    }
}
