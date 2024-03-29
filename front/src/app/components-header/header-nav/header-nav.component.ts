import { Component, ElementRef, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header-nav',
  templateUrl: './header-nav.component.html',
  styleUrls: ['./header-nav.component.scss'],
})
export class HeaderNavComponent implements OnInit {

  menuOpen = false;

  constructor(private router:Router,private elementRef:ElementRef) { }

  ngOnInit(): void {
    
  }

  onSelectChange(event: any) {
    this.router.navigate([event.target.value]);
  }

  onDocumentClick(event: MouseEvent) {
    const clickedInside = this.elementRef.nativeElement.contains(event.target);
    if (!clickedInside) {
        this.menuOpen = false;
    }
  }

}
