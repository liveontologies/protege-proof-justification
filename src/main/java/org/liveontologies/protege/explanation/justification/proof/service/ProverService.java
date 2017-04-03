package org.liveontologies.protege.explanation.justification.proof.service;

/*-
 * #%L
 * Protege Proof Justification Explanation
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2016 - 2017 Live Ontologies Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import org.liveontologies.owlapi.proof.OWLProver;
import org.protege.editor.core.plugin.ProtegePluginInstance;
import org.protege.editor.owl.OWLEditorKit;

/**
 * A skeleton for a plugin that can provide us with a prover
 * 
 * @author Alexander
 * Date: 23/02/2017
 */

public abstract class ProverService implements ProtegePluginInstance {

	public abstract OWLProver getProver(OWLEditorKit ek);
	
	/**
	 * Should return a name for the plugin
	 * 
	 * @return	the name to be displayed in available plugins list
	 */
	public abstract String getName();
	
	@Override
	public String toString() {
		return getName();
	}
}