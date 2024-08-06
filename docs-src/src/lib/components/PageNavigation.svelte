<script lang="ts">
	import { onMount, onDestroy } from 'svelte';
	import { browser } from '$app/environment';

	export let firstLink = '';
	export let prevLink = '';
	export let nextLink = '';
	export let lastLink = '';

	function handleGlobalKeydown(event: KeyboardEvent) {
		if (event.key === 'ArrowLeft' && prevLink) {
			event.preventDefault();
			window.location.href = prevLink;

		} else if (event.key === 'ArrowRight' && nextLink) {
			event.preventDefault();
			window.location.href = nextLink;
		}
	}

	onMount(() => {
		if (browser) {
			window.addEventListener('keydown', handleGlobalKeydown);
		}
	});

	onDestroy(() => {
		if (browser) {
			window.removeEventListener('keydown', handleGlobalKeydown);
		}
	});
</script>

<style>
	.nav {
		/* functional */
		position: absolute;
		bottom: 0px;
		right: 0.5em;
	}

	.nav button {
		/* functional */
		color: #888;
		cursor: pointer;

		/* cosmetic */
		padding-left: 0.5em;
		padding-right: 0.5em;
		background-color: #333;
		font-weight: bold;
		border: 0px;
	}
	.nav button:active {
        /* cosmetic */
		box-shadow: 0 2px #0056b3;
		transform: translateY(3px);
	}
	.nav button:hover {
		/* cosmetic */
		color: #fff;
		background-color: #2980b9;
	}
	.nav button:disabled {
		/* functional */
		color: #444;
		cursor: default;
	}

</style>

<div class="nav">
	<button on:click={() => firstLink && (window.location.href = firstLink)} disabled={!firstLink}>FIRST</button>
	<button on:click={() => prevLink  && (window.location.href = prevLink)}  disabled={!prevLink} >PREV</button>
	<button on:click={() => nextLink  && (window.location.href = nextLink)}  disabled={!nextLink} >NEXT</button>
	<button on:click={() => lastLink  && (window.location.href = lastLink)}  disabled={!lastLink} >LAST</button>
</div>