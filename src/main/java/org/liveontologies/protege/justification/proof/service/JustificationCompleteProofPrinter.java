package org.liveontologies.protege.justification.proof.service;

import java.io.IOException;

import org.liveontologies.puli.ProofPrinter;

public class JustificationCompleteProofPrinter {

	public static <C> void print(JustificationCompleteProof<C> proof)
			throws IOException {
		ProofPrinter.print(proof, proof, proof.getGoal());
	}

}
