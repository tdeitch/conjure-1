/*
 * Copyright 2016 Palantir Technologies, Inc. All rights reserved.
 */

package com.palantir.conjure.gradle;

import static com.google.common.base.Preconditions.checkState;

import com.google.common.io.Files;
import com.palantir.conjure.defs.Conjure;
import com.palantir.conjure.defs.ConjureDefinition;
import com.palantir.conjure.gen.java.ExperimentalFeatures;
import com.palantir.conjure.gen.java.Settings;
import com.palantir.conjure.gen.java.types.BeanGenerator;
import com.palantir.conjure.gen.java.types.TypeGenerator;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;

public class CompileConjureJavaObjectsTask extends SourceTask {

    @OutputDirectory
    private File outputDirectory;

    private Supplier<Set<ExperimentalFeatures>> experimentalFeatures;

    public final void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public final void setExperimentalFeatures(Supplier<Set<ExperimentalFeatures>> experimentalFeatures) {
        this.experimentalFeatures = experimentalFeatures;
    }

    @Input
    public final Set<ExperimentalFeatures> getExperimentalFeatures() {
        return experimentalFeatures.get();
    }

    @TaskAction
    public final void compileFiles() throws IOException {
        checkState(outputDirectory.exists() || outputDirectory.mkdirs(),
                "Unable to make directory tree %s", outputDirectory);

        Settings settings = Settings.builder()
                .ignoreUnknownProperties(true)
                .supportUnknownEnumValues(true)
                .build();

        TypeGenerator generator = new BeanGenerator(settings, experimentalFeatures.get());
        compileFiles(generator, getSource().getFiles());

        // write a gitignore to prevent the generated files ending up in source control
        Files.write("*.java\n", new File(outputDirectory, ".gitignore"), StandardCharsets.UTF_8);
    }

    private void compileFiles(TypeGenerator generator, Collection<File> files) {
        files.forEach(f -> compileFile(generator, f.toPath()));
    }

    private void compileFile(TypeGenerator generator, Path path) {
        ConjureDefinition conjure = Conjure.parse(path.toFile());
        generator.emit(conjure, outputDirectory);
    }
}