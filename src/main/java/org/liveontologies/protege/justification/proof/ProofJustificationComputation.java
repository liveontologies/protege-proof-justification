package org.liveontologies.protege.justification.proof;

import java.util.Set;

import org.liveontologies.protege.explanation.justification.service.JustificationComputation;
import org.liveontologies.protege.explanation.justification.service.JustificationListener;
import org.liveontologies.protege.justification.proof.service.JustificationCompleteProof;
import org.liveontologies.puli.justifications.InterruptMonitor;
import org.liveontologies.puli.justifications.MinimalSubsetEnumerator;
import org.liveontologies.puli.justifications.MinimalSubsetEnumerators;
import org.semanticweb.owlapi.model.OWLAxiom;

public class ProofJustificationComputation extends JustificationComputation
		implements InterruptMonitor,
		MinimalSubsetEnumerator.Listener<OWLAxiom> {

	private final JustificationProofManager manager_;

	public ProofJustificationComputation(JustificationProofManager manager,
			JustificationListener listener,
			JustificationComputation.InterruptMonitor monitor) {
		super(listener, monitor);
		this.manager_ = manager;
	}

	@Override
	public void startComputation() {
		enumerateJustifications(manager_.getProof());
	}

	public <C> void enumerateJustifications(
			JustificationCompleteProof<C> proof) {
		MinimalSubsetEnumerators.enumerateJustifications(proof.getGoal(), proof,
				proof, this, this);
	}

	@Override
	public void newMinimalSubset(Set<OWLAxiom> set) {
		notifyJustificationFound(set);
	}

}
