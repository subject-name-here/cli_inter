package ru.spbau.mit.java.paradov.pipeline_handler.pipeline_command_collection_impl

import ru.spbau.mit.java.paradov.pipeline_handler.PipelineCommandCollection
import ru.spbau.mit.java.paradov.shell.Shell

/**
 * Empty command collection (with zero commands). Does nothing, as it should.
 */
class PipelineCommandEmptyCollection : PipelineCommandCollection() {
    override fun run(shell: Shell) {}
}