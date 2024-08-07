<script lang="ts">
	import { onMount, onDestroy } from 'svelte';
	import { browser } from '$app/environment';
	import CtrlBtn from './CtrlBtn.svelte';
	import { scaleMode } from '$lib/stores/scaleMode';

	function toggleScale() {
		scaleMode.update(current => !current);
	}
</script>

<style>
	@import url('https://fonts.googleapis.com/css2?family=Amatic+SC:wght@400;700&display=swap');

	.copyright {
		/* cosmetic */
		position: absolute;
		padding-left: 0.5em;
		padding-right: 0.5em;
		bottom: 0px;
		right: 0px;
		margin-right: 0.5em;
		font-size: 1.5em;
        font-weight: bold;
		opacity: 0.7;

		font-family: 'Amatic SC', cursive;
	}

    .copyright .hover-text {
        display: none;
    }
    .copyright:hover .hover-text {
        display: inline;
		opacity: 1;
        font-family: 'Playfair Display Regular';
    }

    .copyright:hover .text {
        display: none;
    }
</style>

<div class="copyright">
	<span class="text"      >© NawaNawa 2024</span>
	<span class="hover-text"><a href="https://nawaman.net/contact.html">Copyright <b>NawaNawa &lt;contact@nawaman.net&gt;</b> 2024</a></span>
</div>
