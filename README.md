# clustering – phylogenetic tree reconstruction (Java)

Command-line tool that builds phylogenetic trees from sequences or a distance matrix using
hierarchical clustering and neighbour joining, and exports them in **Newick** format.

> University project (B.Sc. Bioinformatics, FSU Jena – *Data Mining and Sequence Analysis*, winter 2023/24, team of 3).
> Part of a three-step series: [exact-search](https://github.com/anshkhubchandani/exact-search) → [alignments](https://github.com/anshkhubchandani/alignments) → [clustering](https://github.com/anshkhubchandani/clustering), where each project builds on the previous one.

## Features
- **UPGMA** and **WPGMA** (rooted trees) and **Neighbour-Joining** (unrooted trees)
- Input: sequences (distances computed with the alignments project) or a precomputed distance matrix
- Newick output for viewers such as iTOL or FigTree
- Example data: tRNA sequences of *Aquifex* (`src/main/resources/aquifex-tRNA-clean.fasta`)
- JUnit tests for all three methods and the Newick writer

## Build
Requires Java 17+ and Gradle. Depends on **alignments** (which depends on **exact-search**): publish both
with `./gradlew publishToMavenLocal` first, add `mavenLocal()` to the repositories in `build.gradle`,
then run `./gradlew build`.

## Usage
```bash
clustering -c NJ    -s seqs.fasta -o tree.newick
clustering -c UPGMA -m distances.txt -o tree.newick
clustering -h
```

## Tech
Java · Gradle · picocli (CLI) · JUnit 5 · phylogenetics
