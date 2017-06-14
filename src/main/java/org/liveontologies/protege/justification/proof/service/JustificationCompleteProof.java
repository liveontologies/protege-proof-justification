package org.liveontologies.protege.justification.proof.service;

import java.util.Set;

import org.liveontologies.puli.InferenceJustifier;
import org.liveontologies.puli.Proof;
import org.semanticweb.owlapi.model.OWLAxiom;

public interface JustificationCompleteProof<C> extends Proof<C>, InferenceJustifier<C, Set<? extends OWLAxiom>> {

	C getGoal();
}