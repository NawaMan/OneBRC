import{s as L,c as K,o as q,a as B,n as D,d as W,u as G,g as P,e as A,h as F}from"../chunks/scheduler.Bm1vu1fr.js";import{S as O,i as V,e as _,k as y,s as E,c as $,a as S,l as k,f as M,d,m as w,r as z,g as x,n as C,h as b,o as p,p as g,q as T,v as I}from"../chunks/index.XvGQ8ST3.js";import{w as j}from"../chunks/entry.D6bo7-Vw.js";import{C as N}from"../chunks/CtrlBtn.lC4zrQ0f.js";const J=!0,fe=Object.freeze(Object.defineProperty({__proto__:null,prerender:J},Symbol.toStringTag,{value:"Module"})),R=j(!1);function H(l){let e,t='<ol class="svelte-yuxbuk"><li><a href="/" class="svelte-yuxbuk">Title</a></li> <li><a href="/introduction.html" class="svelte-yuxbuk">One Billion Row Chanllenge</a></li></ol>';return{c(){e=_("div"),e.innerHTML=t,this.h()},l(s){e=$(s,"DIV",{class:!0,"data-svelte-h":!0}),I(e)!=="svelte-cnexch"&&(e.innerHTML=t),this.h()},h(){w(e,"class","content svelte-yuxbuk")},m(s,a){x(s,e,a)},d(s){s&&d(e)}}}function Q(l){let e,t,s,a;t=new N({props:{text:"ToC",hoverText:"Table of Content",isSelected:l[0]}}),t.$on("click",l[2]);let n=l[0]&&H();return{c(){e=_("div"),y(t.$$.fragment),s=E(),n&&n.c(),this.h()},l(o){e=$(o,"DIV",{class:!0});var c=S(e);k(t.$$.fragment,c),s=M(c),n&&n.l(c),c.forEach(d),this.h()},h(){w(e,"class","toc no-print svelte-yuxbuk"),z(e,"expanded",l[0])},m(o,c){x(o,e,c),C(t,e,null),b(e,s),n&&n.m(e,null),a=!0},p(o,[c]){const i={};c&1&&(i.isSelected=o[0]),t.$set(i),o[0]?n||(n=H(),n.c(),n.m(e,null)):n&&(n.d(1),n=null),(!a||c&1)&&z(e,"expanded",o[0])},i(o){a||(p(t.$$.fragment,o),a=!0)},o(o){g(t.$$.fragment,o),a=!1},d(o){o&&d(e),T(t),n&&n.d()}}}function U(l,e,t){let s,a=j(!1);K(l,a,i=>t(0,s=i));function n(){a.update(i=>!i)}function o(){a.update(i=>!1)}function c(i){i.ctrlKey&&i.altKey&&i.key==="T"&&(i.preventDefault(),n()),i.key==="Escape"&&(i.preventDefault(),o())}return q(()=>{window.addEventListener("keydown",c)}),B(()=>{window.removeEventListener("keydown",c)}),[s,a,n]}class X extends O{constructor(e){super(),V(this,e,U,Q,L,{})}}function Y(l){let e,t,s;return t=new N({props:{text:"M",hoverText:"MODE"}}),t.$on("click",l[0]),{c(){e=_("div"),y(t.$$.fragment),this.h()},l(a){e=$(a,"DIV",{class:!0});var n=S(e);k(t.$$.fragment,n),n.forEach(d),this.h()},h(){w(e,"class","mode no-print svelte-mofkhc")},m(a,n){x(a,e,n),C(t,e,null),s=!0},p:D,i(a){s||(p(t.$$.fragment,a),s=!0)},o(a){g(t.$$.fragment,a),s=!1},d(a){a&&d(e),T(t)}}}function Z(l){function e(){R.update(t=>!t)}return[e]}class ee extends O{constructor(e){super(),V(this,e,Z,Y,L,{})}}function te(l){let e,t='<span class="text svelte-8mz42n">© NawaNawa 2024</span> <span class="hover-text svelte-8mz42n"><a href="https://nawaman.net/contact.html">Copyright <b>NawaNawa &lt;contact@nawaman.net&gt;</b> 2024</a></span>';return{c(){e=_("div"),e.innerHTML=t,this.h()},l(s){e=$(s,"DIV",{class:!0,"data-svelte-h":!0}),I(e)!=="svelte-135mwj1"&&(e.innerHTML=t),this.h()},h(){w(e,"class","copyright svelte-8mz42n")},m(s,a){x(s,e,a)},p:D,i:D,o:D,d(s){s&&d(e)}}}function ne(l){return[]}class se extends O{constructor(e){super(),V(this,e,ne,te,L,{})}}function ae(l){let e,t,s,a,n,o,c,i,f;s=new X({}),n=new ee({});const v=l[3].default,u=W(v,l,l[2],null);return i=new se({}),{c(){e=_("div"),t=_("div"),y(s.$$.fragment),a=E(),y(n.$$.fragment),o=E(),u&&u.c(),c=E(),y(i.$$.fragment),this.h()},l(r){e=$(r,"DIV",{class:!0});var h=S(e);t=$(h,"DIV",{class:!0});var m=S(t);k(s.$$.fragment,m),a=M(m),k(n.$$.fragment,m),o=M(m),u&&u.l(m),c=M(m),k(i.$$.fragment,m),m.forEach(d),h.forEach(d),this.h()},h(){w(t,"class","content svelte-nk26dm"),w(e,"class","container svelte-nk26dm"),z(e,"scale-mode",l[1])},m(r,h){x(r,e,h),b(e,t),C(s,t,null),b(t,a),C(n,t,null),b(t,o),u&&u.m(t,null),b(t,c),C(i,t,null),l[4](e),f=!0},p(r,[h]){u&&u.p&&(!f||h&4)&&G(u,v,r,r[2],f?A(v,r[2],h,null):P(r[2]),null),(!f||h&2)&&z(e,"scale-mode",r[1])},i(r){f||(p(s.$$.fragment,r),p(n.$$.fragment,r),p(u,r),p(i.$$.fragment,r),f=!0)},o(r){g(s.$$.fragment,r),g(n.$$.fragment,r),g(u,r),g(i.$$.fragment,r),f=!1},d(r){r&&d(e),T(s),T(n),u&&u.d(r),T(i),l[4](null)}}}function le(l,e,t){let{$$slots:s={},$$scope:a}=e,n,o=!1;R.subscribe(f=>{t(1,o=f),o?(c(),window.addEventListener("resize",c)):(window.removeEventListener("resize",c),n&&(t(0,n.style.width="1280px",n),t(0,n.style.height="720px",n)))});function c(){if(!n)return;const f=1280/720,v=window.innerWidth,u=window.innerHeight;v/u>f?(t(0,n.style.height="100vh",n),t(0,n.style.width=`${100*f}vh`,n)):(t(0,n.style.width="100vw",n),t(0,n.style.height=`${100/f}vw`,n))}function i(f){F[f?"unshift":"push"](()=>{n=f,t(0,n)})}return l.$$set=f=>{"$$scope"in f&&t(2,a=f.$$scope)},[n,o,a,s,i]}class ue extends O{constructor(e){super(),V(this,e,le,ae,L,{})}}export{ue as component,fe as universal};
