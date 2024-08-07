<script lang="ts">
	import { onMount, onDestroy } from 'svelte';
	import { browser } from '$app/environment';

    export let text       = 'Btn';
	export let isSelected = false;
	export let isDisabled = false;
</script>

<style>
    button {
		/* functional */
		cursor: pointer;

		/* cosmetic */
		padding-left: 0.5em;
		padding-right: 0.5em;
		margin-left: 0.5em;
		text-justify: center;
		text-align: center;
		background-color: #333;
		color: #888;
		font-weight: bold;
		border: 0px;
	}

	button:hover {
		/* cosmetic */
		color: #fff;
		background-color: #2980b9;
	}
	button.selected {
		/* cosmetic */
		color: #fff;
		background-color: green;
	}
	button:active:not(:disabled) {
		/* cosmetic */
		box-shadow: 0 2px #0056b3;
		transform: translateY(3px);
	}
	button:disabled {
		/* functional */
		color: #444;
		cursor: default;
	}
</style>

<button 
	disabled={isDisabled} 
	class:selected={isSelected} 
	on:click>
	{text}
</button>
