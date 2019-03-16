package ru.spbau.mit.java.paradov.pipeline_handler

import ru.spbau.mit.java.paradov.shell.Shell

/**
 * Interface for keeping commands that originally were split by pipe.
 */
abstract class PipelineCommandCollection {
    /**
     * Runs commands in pipeline.
     */
    abstract fun run(shell: Shell)
}