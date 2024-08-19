<script lang="javascript">
	import { onMount } from 'svelte';
	import { browser } from '$app/environment';

	export let javaCode    = '';
	export let width       = '800px';
	export let height      = '400px';

	// @ts-ignore
	export let revealLines = [];

	// @ts-ignore
	let editor;
	// @ts-ignore
	let editorElement;

	onMount(() => {
		const script = document.createElement('script');
		script.src = 'https://cdnjs.cloudflare.com/ajax/libs/monaco-editor/0.21.2/min/vs/loader.js';
		script.onload = initMonaco;
		document.body.appendChild(script);

		return () => {
			// @ts-ignore
			if (editor) {
				editor.dispose();
			}
		};
	});

	function initMonaco() {
		if (browser) {
			// @ts-ignore
			window.require.config({
				paths: { vs: 'https://cdnjs.cloudflare.com/ajax/libs/monaco-editor/0.21.2/min/vs' }
			});
			// @ts-ignore
			window.require(['vs/editor/editor.main'], function () {
				// @ts-ignore
				editor = monaco.editor.create(editorElement, {
					value: javaCode,
					language: 'java',
					minimap: { enabled: true },
					folding: true,
					automaticLayout: true,
					readOnly: true,
					theme: "vs-dark",
					foldingImportsByDefault: true
				});

				const customFoldingRangeProvider = {
					// @ts-ignore
					provideFoldingRanges: function (model, context, token) {
						let ranges = [];
						let importStartLine = -1;
						let importEndLine = -1;

						for (let i = 1; i <= model.getLineCount(); i++) {
							let line = model.getLineContent(i);

							// Check for import statements
							if (line.trim().startsWith('import ')) {
								if (importStartLine === -1) {
									importStartLine = i;
								}
								importEndLine = i;
							} else if (importStartLine !== -1) {
								// Add folding range for imports
								ranges.push({
									start: importStartLine,
									end: importEndLine,
									// @ts-ignore
									kind: monaco.languages.FoldingRangeKind.Imports
								});
								importStartLine = -1;
								importEndLine = -1;
							}

							// Existing code for brace folding
							if (line.trim().endsWith('{')) {
								let startLine = i;
								let endLine = findMatchingBrace(model, i);
								if (endLine > startLine) {
									ranges.push({
										start: startLine,
										end: endLine,
										// @ts-ignore
										kind: monaco.languages.FoldingRangeKind.Region
									});
								}
							}
						}

						// Check if there are imports at the end of the file
						if (importStartLine !== -1) {
							ranges.push({
								start: importStartLine,
								end: importEndLine,
								// @ts-ignore
								kind: monaco.languages.FoldingRangeKind.Imports
							});
						}

						return ranges;
					}
				};

				// @ts-ignore
				function findMatchingBrace(model, startLine) {
					let braceCount = 1;
					for (let i = startLine + 1; i <= model.getLineCount(); i++) {
						let line = model.getLineContent(i);
						braceCount += (line.match(/{/g) || []).length;
						braceCount -= (line.match(/}/g) || []).length;
						if (braceCount === 0) {
							return i;
						}
					}
					return startLine;
				}

				// @ts-ignore
				monaco.languages.registerFoldingRangeProvider('java', customFoldingRangeProvider);

				// @ts-ignore
				function unfoldLines(lineNumbers) {
					// @ts-ignore
					lineNumbers.forEach(lineNumber => {
						if(Object.prototype.toString.call(lineNumber) === '[object Array]') {
							setTimeout(function () {
								unfoldLines(lineNumber)
							}, 200);
							return;
						}
						// @ts-ignore
						editor.setSelection({
							startLineNumber: lineNumber,
							startColumn: 1,
							endLineNumber: lineNumber + 1,
							endColumn: 1
						});
						// @ts-ignore
						editor.trigger('keyboard', 'editor.unfold');
					});
				}

				setTimeout(function () {
					// @ts-ignore
					editor.trigger('keyboard', 'editor.foldAll');
					// @ts-ignore
					unfoldLines(revealLines);
				}, 200);
			});
		}
	}
</script>

<div bind:this={editorElement} style="width: {width}; height: {height};"></div>
